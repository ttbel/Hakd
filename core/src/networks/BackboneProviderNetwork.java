package networks;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import game.Internet;
import gui.HakdSprite;
import other.Line;

import java.util.HashMap;
import java.util.Set;

/**
 * Backbones are the infrastructure that make up the internet. Backbones connect
 * ISPs to the internet, as well as connecting other backboneProviderNetworks
 * together.
 */
public class BackboneProviderNetwork extends Network {
    Set<Sprite> backboneConnectionLines;
    private final HashMap<String, InternetProviderNetwork> ipChildNetworkHashMap = new HashMap<String, InternetProviderNetwork>(255);

    public static final int maxDistance = 5000;

    public BackboneProviderNetwork(Internet internet) {
        this.internet = internet;
    }

    /**
     * For ISP networks.
     */
    public void registerNewIsp(InternetProviderNetwork isp, int speed) {
        isp.setParent(this);
        isp.setIp(internet.assignIp(isp));
        isp.setSpeed(speed);
        ipChildNetworkHashMap.put(isp.getIp(), isp);

        // connection line between isp and backbone(this)
        HakdSprite mapIcon = getMapIcon();
        HakdSprite ispMapIcon = isp.getMapIcon();
        Vector2 v1 = new Vector2(mapIcon.getX() + (mapIcon.getWidth() / 2), mapIcon.getY() + (mapIcon.getHeight() / 2));
        Vector2 v2 = new Vector2(ispMapIcon.getX() + (ispMapIcon.getWidth() / 2), ispMapIcon.getY() + (ispMapIcon.getHeight() / 2));
        isp.setMapParentLine(new Line(v1, v2));
    }

    public enum BackboneName { // these may change
        /*
         * these are just some names I came up with, they are in no way
         * referencing real companies. for now I will use the greek alphabeta
         * until I think of company names
         *
         * Lynk(), T1Com(), Spirit(), Wnetwork(), KableTown(), WalkerInc(),
         * BlackHat();
         */
        Alpha(), Beta(), Gamma(), Delta(), Epsilon(), Zeta(), Eta(), Theta(), Iota(), Kappa(), Lambda(), Mu(), Nu(),
        Xi(), Omnicron(), Pi(), Rho(), Sigma(), Tau(), Upsilon(), Phi(), Chi(), Psi(), Omega();

        // I don't think I will get very indepth with these.

        BackboneName() {
        }

    }

    public Set<Sprite> getBackboneConnectionLines() {
        return backboneConnectionLines;
    }

    public void setBackboneConnectionLines(Set<Sprite> backboneConnectionLines) {
        this.backboneConnectionLines = backboneConnectionLines;
    }

    public HashMap<String, InternetProviderNetwork> getIpChildNetworkHashMap() {
        return ipChildNetworkHashMap;
    }
}
