package theCube.data.mojang.api;

import theCube.TheCube;

import javax.xml.bind.DatatypeConverter;

public final class UserPropertyRaw{
    public final String name;
    public final String value;

    public UserPropertyRaw(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public UserProperty parse(){
        return TheCube.MAPPER.fromJson(DatatypeConverter.parseBase64Binary(this.value), UserProperty.class);
    }
}