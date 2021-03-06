package networks;

import com.badlogic.gdx.math.Vector2;
import game.Internet;
import gui.HakdSprite;
import other.Line;

import java.util.HashMap;

/**
 * Provides internet access to networks.
 */
public class InternetProviderNetwork extends Network {
    private final HashMap<String, Network> ipChildNetworkHashMap = new HashMap<String, Network>(255);

    public static final int MAX_DISTANCE = 1000;

    public InternetProviderNetwork(Internet internet) {
        this.internet = internet;
    }

    /**
     * For non-provider networks.
     */
    public void registerNewNetwork(Network network, int speed) {
        network.setParent(this);
        network.setIp(internet.assignIp(network));
        network.setSpeed(speed);
        ipChildNetworkHashMap.put(network.getIp(), network);

        // connection line between isp and backbone(this)
        HakdSprite mapIcon = getMapIcon();
        HakdSprite networkMapIcon = network.getMapIcon();
        Vector2 v1 = new Vector2(mapIcon.getX() + (mapIcon.getWidth() / 2), mapIcon.getY() + (mapIcon.getHeight() / 2));
        Vector2 v2 = new Vector2(networkMapIcon.getX() + (networkMapIcon.getWidth() / 2), networkMapIcon.getY() + (networkMapIcon.getHeight() / 2));
        network.setMapParentLine(new Line(v1, v2));
    }

    public void unregister() {

    }

    public enum IspName {
        /*
         * these are just some names I came up with, they are in no way
         * referencing real companies, infinity LTD. for now I will use the
         * greek alphabet until I think of company names
         */
        Alpha(), Beta(), Gamma(), Delta(), Epsilon(), Zeta(), Eta(), Theta(), Iota(), Kappa(), Lambda(), Mu(), Nu(),
        Xi(), Omnicron(), Pi(), Rho(), Sigma(), Tau(), Upsilon(), Phi(), Chi(), Psi(), Omega();

		/*
         * public int price; // I think I can use gooeyFiber(70, 99), maybe name
		 * all fiber isp's after liquids public int level;
		 */

        IspName(/* , int price in $, int level 0-00 */) {
            // this.price = price;
            // this.level = level;
        }
    }

    public HashMap<String, Network> getIpChildNetworkHashMap() {
        return ipChildNetworkHashMap;
    }
}
