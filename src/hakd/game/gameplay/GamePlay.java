package hakd.game.gameplay;

import hakd.internet.NetworkController;
import hakd.networks.Network.NetworkType;
import hakd.networks.ServiceProvider;
import hakd.other.names.Isp;
import hakd.other.names.Owner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GamePlay {

    public static void generateGame() {
	NetworkController.getOwners().addAll(Arrays.asList(Owner.values()));
	// brands and models don't need this because you can have two of the
	// same brand

	generateIsps();
	generateNetworks();
    }

    // creates the game ISPs
    @SuppressWarnings("deprecation")
    private static void generateIsps() {
	int amount = (int) (Math.random() * 6 + 6);
	List<Isp> names = new ArrayList<Isp>(
		Arrays.asList(Isp.values().clone()));

	for (int i = 0; i < amount; i++) {
	    int random = (int) (Math.random() * names.size());
	    ServiceProvider s = new ServiceProvider(names.get(random));

	    s.setIp(s.register(s, 10));
	    names.remove(random);
	    NetworkController.getPublicDns().add(s.getDns());
	    NetworkController.getServiceProviders().add(s);
	    NetworkController.getPublicNetworks().add(s); // this may not be the
							  // best but lets see
							  // what happens
	}
    }

    // creates the initial game networks
    private static void generateNetworks() {
	int amount = (int) (Math.random() * 6 + 30);

	for (int i = 0; i < amount; i++) {
	    NetworkController.addPublicNetwork(NetworkType.TEST);
	}
    }
}
