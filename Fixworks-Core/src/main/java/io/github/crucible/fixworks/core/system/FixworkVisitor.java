package io.github.crucible.fixworks.core.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import io.github.crucible.grimoire.common.api.eventbus.CoreEventHandler;
import io.github.crucible.grimoire.common.api.grimmix.Grimmix;

public class FixworkVisitor extends ClassVisitor {
    private boolean validCandidate = false;
    private String className = null;
    private String fixworkID = Fixwork.DEFAULT_ID;
    private String fixworkDesc = Fixwork.DEFAULT_DESC;
    private String validatorClass = null;
    private String incompatibleClass = null;
    private long priority = Fixwork.DEFAULT_PRIORITY;
    private boolean defaultEnabled = Fixwork.DEFAULT_ENABLED;

    private final List<String> dependencies = new ArrayList<>();

    public FixworkVisitor() {
        super(Opcodes.ASM4);
    }

    public boolean isValidCandidate() {
        return this.className != null && this.validCandidate && this.fixworkID != null;
    }

    public String getClassName() {
        return this.className;
    }

    public String getFixworkID() {
        return this.fixworkID;
    }

    public long getPriority() {
        return this.priority;
    }

    public String getValidatorClass() {
        return this.validatorClass;
    }

    public String getIncompatibleClass() {
        return this.incompatibleClass;
    }

    public String getFixworkDesc() {
        return this.fixworkDesc;
    }

    public boolean isDefaultEnabled() {
        return this.defaultEnabled;
    }

    public List<String> getDependencies() {
        return this.dependencies;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        byte annoType = -1;

        if (getDescriptorForClass(Fixwork.class).equals(desc)) {
            this.validCandidate = true;
            annoType = 0;
        } else if (getDescriptorForClass(ValidatorClass.class).equals(desc)) {
            annoType = 1;
        } else if (getDescriptorForClass(IncompatibleClass.class).equals(desc)) {
            annoType = 2;
        }

        return annoType != -1 ? new DataReader(this, annoType) : null;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.className = name != null ? name.replaceAll("/", ".") : "null";
        super.visit(version, access, name, signature, superName, interfaces);
    }

    private static class DataReader extends AnnotationVisitor {
        private final FixworkVisitor supervisitor;
        private final byte annoType;

        private DataReader(FixworkVisitor supervisitor, byte annoType) {
            super(Opcodes.ASM4);
            this.supervisitor = supervisitor;
            this.annoType = annoType;
        }

        @Override
        public AnnotationVisitor visitArray(String name) {
            if ("depends".equals(name))
                return this;
            else
                return super.visitArray(name);
        }

        /**
         * @see {@link Fixwork#id()}, {@link Fixwork#priority()}, {@link Fixwork#desc()},
         * {@link ValidatorClass#value()}, {@link IncompatibleClass#value()}
         */
        @Override
        public void visit(String name, Object value) {
            if (this.annoType == 0) {
                System.out.println("Name: " + name + ", value: " + value);
                if ("id".equals(name)) {
                    this.supervisitor.fixworkID = String.valueOf(value);
                } else if ("priority".equals(name)) {
                    this.supervisitor.priority = (long) value;
                } else if ("desc".equals(name)) {
                    this.supervisitor.fixworkDesc = String.valueOf(name);
                } else if ("defaultEnabled".equals(name)) {
                    this.supervisitor.defaultEnabled = (boolean) value;
                } else if (name == null && value instanceof String) {
                    this.supervisitor.dependencies.add((String) value);
                }
            } else if (this.annoType == 1) {
                if ("value".equals(name)) {
                    this.supervisitor.validatorClass = String.valueOf(value);
                }
            } else if (this.annoType == 2) {
                if ("value".equals(name)) {
                    this.supervisitor.incompatibleClass = String.valueOf(value);
                }
            }

            super.visit(name, value);
        }
    }

    public static FixworkVisitor examineClass(File classFile) {
        try {
            FileInputStream stream = new FileInputStream(classFile);
            FixworkVisitor result = examineClass(stream);
            stream.close();

            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static FixworkVisitor examineClass(ZipFile archive, ZipEntry entry) {
        try {
            InputStream stream = archive.getInputStream(entry);
            FixworkVisitor result = examineClass(stream);
            stream.close();

            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static FixworkVisitor examineClass(InputStream classStream) {
        try {
            ClassReader reader = new ClassReader(classStream);
            FixworkVisitor visitor = new FixworkVisitor();
            reader.accept(visitor, 0);

            return visitor;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String getDescriptorForClass(Class<?> classInQuestion) {
        return Type.getDescriptor(classInQuestion);
    }
}
