package theCube.data.mojang.api;

import java.util.List;

public final class MinecraftProfileResponse{
    public final List<UserPropertyRaw> properties;

    public MinecraftProfileResponse(List<UserPropertyRaw> properties){
        this.properties = properties;
    }

    public UserProperty property(String name){
        for(UserPropertyRaw prop : this.properties){
            if(name.equals(prop.name)){
                return prop.parse();
            }
        }

        throw new NullPointerException("Property " + name + " doesnt' exist");
    }
}