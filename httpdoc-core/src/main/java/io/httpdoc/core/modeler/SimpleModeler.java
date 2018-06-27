package io.httpdoc.core.modeler;

import io.httpdoc.core.*;
import io.httpdoc.core.exception.SchemaDesignException;
import io.httpdoc.core.fragment.*;
import io.httpdoc.core.provider.Provider;
import io.httpdoc.core.type.HDClass;
import io.httpdoc.core.type.HDType;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * 简单的模型师
 *
 * @author 杨昌沛 646742615@qq.com
 * @date 2018-05-18 11:15
 **/
public class SimpleModeler implements Modeler {
    private final Preference preference;

    public SimpleModeler() {
        this(Preference.DEFAULT);
    }

    public SimpleModeler(Preference preference) {
        this.preference = preference;
    }

    @Override
    public Model design(Archetype archetype) throws SchemaDesignException {
        String comment = "Generated By Httpdoc";
        String pkg = archetype.getPkg();
        boolean pkgForced = archetype.isPkgForced();
        Provider provider = archetype.getProvider();
        Schema schema = archetype.getSchema();
        String name = schema.getName();
        ClassFragment clazz = new ClassFragment();
        clazz.setPkg(pkg);
        clazz.setCommentFragment(new CommentFragment(schema.getDescription() != null ? schema.getDescription() + "\n" + comment : comment));
        switch (schema.getCategory()) {
            case ENUM:
                clazz.setClazz(new HDClass(HDClass.Category.ENUM, pkg + "." + name));
                Set<Constant> constants = schema.getConstants();
                for (Constant constant : (constants != null ? constants : Collections.<Constant>emptySet())) {
                    ConstantFragment con = new ConstantFragment(new CommentFragment(constant.getDescription()), constant.getName());
                    clazz.getConstantFragments().add(con);
                }
                break;
            case OBJECT:
                clazz.setClazz(new HDClass(HDClass.Category.CLASS, pkg + "." + name));
                clazz.setSuperclass(schema.getSuperclass() != null && schema.getSuperclass().getCategory() == Category.OBJECT ? new HDClass(pkg + "." + schema.getSuperclass().getName()) : null);
                Map<String, Property> properties = schema.getProperties();
                for (Map.Entry<String, Property> entry : (properties != null ? properties.entrySet() : Collections.<Map.Entry<String, Property>>emptySet())) {
                    Property property = entry.getValue();
                    HDType type = property.getType().toType(pkg, provider);
                    FieldFragment field = new FieldFragment();
                    field.setName(entry.getKey());
                    field.setType(type);
                    field.setCommentFragment(new CommentFragment(property.getDescription()));
                    clazz.getFieldFragments().add(field);

                    GetterMethodFragment getter = new GetterMethodFragment(type, entry.getKey());
                    clazz.getMethodFragments().add(getter);

                    SetterMethodFragment setter = new SetterMethodFragment(type, entry.getKey());
                    clazz.getMethodFragments().add(setter);
                }
                break;
        }

        return new BasicModel(clazz, preference);
    }

}
