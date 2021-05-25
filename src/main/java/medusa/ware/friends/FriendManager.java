// 
// Decompiled by Procyon v0.5.36
// 

package medusa.ware.friends;

import medusa.ware.config.ConfigManager;
import medusa.ware.MedusaWare;
import java.util.ArrayList;
import java.util.List;

public class FriendManager
{
    private List<String> friends;
    
    public void addFriend(final String s) {
        if (this.friends == null) {
            (this.friends = new ArrayList<String>()).clear();
        }
        this.friends.add(s);
        final ConfigManager configManager = MedusaWare.instance.configManager;
        ConfigManager.SaveFriendsFile();
    }
    
    public void addFriendNoSave(final String s) {
        this.friends.add(s);
    }
    
    public List<String> getFriends() {
        if (this.friends == null) {
            (this.friends = new ArrayList<String>()).clear();
        }
        return this.friends;
    }
}
