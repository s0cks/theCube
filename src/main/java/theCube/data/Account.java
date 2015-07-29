package theCube.data;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import theCube.TheCube;
import theCube.data.mojang.api.MinecraftProfileResponse;
import theCube.data.mojang.api.Profile;
import theCube.utils.FileSystem;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public final class Account{
    public final String username;
    public final Color sidebarColor;
    private String uuid;

    public Account(String username, Color sidebarColor) {
        this.username = username;
        this.sidebarColor = sidebarColor;
    }

    public BufferedImage getSkin(){
        Path skin = FileSystem.SKINS.resolve(this.username.toLowerCase() + ".png");
        if(!Files.exists(skin)) {
            try{
                Request req = new Request.Builder()
                        .url(this.getSkinUrl())
                        .build();
                Response resp = TheCube.CLIENT.newCall(req).execute();

                try(ReadableByteChannel rbc = Channels.newChannel(resp.body().byteStream());
                    FileChannel wbc = FileChannel.open(skin, StandardOpenOption.CREATE, StandardOpenOption.WRITE)){

                    wbc.transferFrom(rbc, 0, Long.MAX_VALUE);
                }
            } catch(Exception e){
                e.printStackTrace(System.err);
                return null;
            }
        }

        try{
            return ImageIO.read(Files.newInputStream(skin));
        } catch(Exception e){
            e.printStackTrace(System.err);
            return null;
        }
    }

    public String getUUID(){
        if(this.uuid == null){
            try{
                Request req = new Request.Builder()
                                      .url("https://api.mojang.com/users/profiles/minecraft/" + this.username)
                                      .build();
                Response resp = TheCube.CLIENT.newCall(req).execute();
                if(resp.isSuccessful()){
                    Profile profile = TheCube.MAPPER.fromJson(resp.body().byteStream(), Profile.class);
                    this.uuid = profile.id;
                }
            } catch(Exception e){
                this.uuid = "0";
            }
        }

        return this.uuid;
    }

    private String getUUIDNoDashes(){
        return this.getUUID().replace("-", "");
    }

    private String getSkinUrl(){
        try{
            Request req = new Request.Builder()
                                  .url("https://sessionserver.mojang.com/session/minecraft/profile/" + this.getUUIDNoDashes())
                                  .build();
            Response resp = TheCube.CLIENT.newCall(req).execute();

            MinecraftProfileResponse mcProfile = TheCube.MAPPER.fromJson(resp.body().byteStream(), MinecraftProfileResponse.class);

            if(mcProfile.properties == null){
                throw new NullPointerException("Properties == null");
            }

            return mcProfile.property("textures").getTexture("SKIN").url;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public Image getMinecraftHead(){
        BufferedImage skin = this.getSkin();
        BufferedImage main = skin.getSubimage(8, 8, 8, 8);
        BufferedImage head = new BufferedImage(8, 8, BufferedImage.TYPE_INT_ARGB);
        Graphics g = head.getGraphics();
        g.drawImage(main, 0, 0, null);
        return head.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
    }
}