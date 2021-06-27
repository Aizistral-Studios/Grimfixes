package io.github.crucible.fixworks.core;

import io.github.crucible.fixworks.core.system.FixworkContainer;
import io.github.crucible.fixworks.core.system.FixworkVisitor;
import io.github.crucible.grimoire.common.api.grimmix.Grimmix;
import io.github.crucible.grimoire.common.api.grimmix.GrimmixController;
import io.github.crucible.grimoire.common.api.grimmix.IGrimmix;
import io.github.crucible.grimoire.common.api.grimmix.lifecycle.IConfigBuildingEvent;
import io.github.crucible.grimoire.common.api.grimmix.lifecycle.IFinishLoadEvent;
import io.github.crucible.grimoire.common.api.grimmix.lifecycle.IValidationEvent;
import io.github.crucible.grimoire.common.api.mixin.IMixinConfiguration;
import io.github.crucible.omniconfig.api.OmniconfigAPI;
import io.github.crucible.omniconfig.api.OmniconfigConstants;
import io.github.crucible.omniconfig.api.builders.IOmniconfigBuilder;
import io.github.crucible.omniconfig.api.core.VersioningPolicy;
import io.github.crucible.omniconfig.api.lib.Version;

import static io.github.crucible.fixworks.core.FixworksCore.*;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.service.MixinService;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;

@Grimmix(id = ID, name = NAME, version = VERSION)
public class FixworksCore extends GrimmixController {
    public static final String ID = "Fixworks";
    public static final String NAME = "Fixworks";
    public static final String VERSION = "@VERSION@";
    public static final String REFMAP_NAME = "@MIXIN_REFMAP@";

    private static final String CHADMC_MODULES_PACKAGE = "io.github.crucible.fixworks.chadmc";
    private static final String INCELMC_MODULES_PACKAGE = "io.github.crucible.fixworks.incelmc";

    private static final String CHADMC_MODULES_PATH = CHADMC_MODULES_PACKAGE.replace(".", "/");
    private static final String INCELMC_MODULES_PATH = INCELMC_MODULES_PACKAGE.replace(".", "/");
    public static final Logger logger = LogManager.getLogger("Fixworks");

    private static FixworksCore instance = null;

    private final List<FixworkContainer> fixworks = new ArrayList<>();
    private IGrimmix grimmix = null;
    private File file = null;

    public FixworksCore() {
        super();
        instance = this;
    }

    @Override
    public void validateController(IValidationEvent event) {
        try {
            logger.info("I validate, hereby, I exist.");

            this.grimmix = event.getOwner();
            this.file = this.grimmix.getGrimmixFile();

            IOmniconfigBuilder builder = OmniconfigAPI.configBuilder("fixworks/Fixworks.cfg", new Version("1.0.0"));
            builder.versioningPolicy(VersioningPolicy.NOBLE);
            builder.pushCategory(OmniconfigConstants.GENERAL_CATEGORY);
            builder.synchronize(false);
            builder.loadFile();

            logger.info("Started module scan...");

            if (this.file.isDirectory()) {
                this.scanDirectories();
            } else {
                this.scanOwnJar();
            }

            Collections.sort(this.fixworks);
            Collections.reverse(this.fixworks);

            logger.info("Total of {} modules were located. The list goes as following:", this.fixworks.size());
            this.fixworks.forEach(fix -> logger.info("Module: {}", fix.getID()));

            this.fixworks.removeIf(fix -> {
                boolean remove = !builder.getBoolean(fix.getID(), fix.isDefaultEnabled())
                        .comment(fix.getDescription() + (fix.hasDependencies() ? " " + fix.depsDesc() : ""))
                        .build().getValue();

                if (remove) {
                    logger.info("Module {} is disabled in config, dropping it...", fix.getID());
                }

                return remove;
            });

            this.fixworks.forEach(fix -> fix.construct());
            this.fixworks.forEach(fix -> fix.verifyDependencies());

            builder.popCategory();
            builder.build();

        } catch (Exception ex) {
            Throwables.propagate(ex);
        }
    }

    @Override
    public void buildMixinConfigs(IConfigBuildingEvent event) {
        for (FixworkContainer container : this.fixworks) {
            List<IMixinConfiguration> configs = this.grimmix.getOwnedConfigurations();
            int prevSize = configs.size();
            container.sendConfig(event);
            int size = configs.size();

            if (size > prevSize) {
                for (int i = prevSize; i < size; i++) {
                    IMixinConfiguration configuration = configs.get(i);
                    container.ownConfig(configuration);
                }
            }
        }
    }

    @Override
    public void finish(IFinishLoadEvent event) {
        // Since they've done their work by now, release references and
        // let Java's garbage collector consume their innocent souls
        this.fixworks.clear();
    }

    private void scanOwnJar() throws Exception {
        JarFile jar = new JarFile(this.file);
        Enumeration<JarEntry> entries = jar.entries();

        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String name = entry.getName();
            String mcPackage = null;

            if (name.startsWith(CHADMC_MODULES_PATH)) {
                mcPackage = CHADMC_MODULES_PATH;
            } else if (name.startsWith(INCELMC_MODULES_PATH)) {
                mcPackage = INCELMC_MODULES_PATH;
            }

            if (mcPackage != null) {
                String subName = name.substring(mcPackage.length()+1, name.length());

                if (subName.contains("/")) {
                    String[] splitName = subName.split(Pattern.quote("/"));
                    if (splitName != null && splitName.length == 2) {
                        String pkg = mcPackage + "/" + splitName[0];
                        subName = splitName[1];
                        pkg = pkg.replace("/", ".");

                        if (!subName.contains("/") && subName.endsWith(".class") && !Objects.equals("package-info.class", subName)) {
                            FixworkVisitor visitor = FixworkVisitor.examineClass(jar, entry);

                            if (visitor.isValidCandidate()) {
                                this.fixworks.add(new FixworkContainer(visitor, pkg));
                            }
                        }
                    }
                }
            }
        }

        jar.close();
    }

    private void scanDirectories() throws Exception {
        for (URL url : MixinService.getService().getClassProvider().getClassPath()) {
            URI uri = url.toURI();

            if (!"file".equals(uri.getScheme()) || !new File(uri).exists()) {
                continue;
            }

            File candidateFile = new File(url.toURI().getPath());

            if (candidateFile.isDirectory()) {
                File[] mcs = new File[2];
                mcs[0] = new File(candidateFile, CHADMC_MODULES_PATH.replace("/", File.separator));
                mcs[1] = new File(candidateFile, INCELMC_MODULES_PATH.replace("/", File.separator));

                for (int i = 0; i < 2; i++) {
                    File mc = mcs[i];

                    if (mc.exists() && mc.isDirectory()) {
                        File[] list = mc.listFiles();

                        if (list != null && list.length > 0) {
                            for (File moduleDir : list) {
                                if (moduleDir.isDirectory()) {
                                    File[] moduleFiles = moduleDir.listFiles();

                                    if (moduleFiles != null && moduleFiles.length > 0) {
                                        for (File moduleFile : moduleFiles) {
                                            if (moduleFile.isFile() && moduleFile.getName().endsWith(".class")
                                                    && !Objects.equals("package-info.class", moduleFile.getName())) {
                                                FixworkVisitor visitor = FixworkVisitor.examineClass(moduleFile);

                                                if (visitor.isValidCandidate()) {
                                                    String pkg = i > 0 ? INCELMC_MODULES_PACKAGE : CHADMC_MODULES_PACKAGE;
                                                    pkg += "." + moduleDir.getName();
                                                    this.fixworks.add(new FixworkContainer(visitor, pkg));
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public List<FixworkContainer> getFixworks() {
        return this.fixworks;
    }

    protected FixworkContainer seekOwner(IMixinConfiguration config) {
        for (FixworkContainer fix : this.fixworks) {
            if (fix.isOwner(config))
                return fix;
        }

        return null;
    }

    public IGrimmix getGrimmix() {
        return this.grimmix;
    }

    public static FixworksCore getInstance() {
        return instance;
    }
}