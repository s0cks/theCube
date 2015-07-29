package theCube.data.mojang.api;

import java.util.Map;

public final class UserProperty{
    public final String profileId;
    public final String profileName;
    public final Map<String, ProfileTexture> textures;

    public UserProperty(String profileId, String profileName, Map<String, ProfileTexture> textures) {
        this.profileId = profileId;
        this.profileName = profileName;
        this.textures = textures;
    }

    public ProfileTexture getTexture(String str){
        if(!this.textures.containsKey(str)){
            throw new NullPointerException("Texture " + str + " doesn't exist");
        }

        return this.textures.get(str);
    }
}