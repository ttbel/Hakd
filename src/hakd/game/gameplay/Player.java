package hakd.game.gameplay;

import hakd.gui.windows.Terminal;
import hakd.networks.Network;
import hakd.networks.devices.Device;

public class Player {
	// player stats
	private int			money;			// in $ //add redundancy to money // triple redundancy with voting, maybe some rudimentary encryption, or no
// redundancy with strong encryption
	private String		name;
	private Network		home;			// meant to be used as the players home base
	private Network		currentNetwork;
	private Device		currentServer;

	private int			x;
	private int			y;

	private Terminal	terminal;		// TODO replace this with "private window" that will hold the current window being looked at

	// --------methods--------
	public Player(String name, Network home, Terminal terminal) {
		this.name = name;
		this.home = home;
		this.currentNetwork = home;
		this.currentServer = home.getMasterServer();
		this.terminal = terminal;

	}

	// --------getters/setters--------

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public Network getHome() {
		return home;
	}

	public void setHome(Network home) {
		this.home = home;
	}

	public Network getCurrentNetwork() {
		return currentNetwork;
	}

	public void setCurrentNetwork(Network currentNetwork) {
		this.currentNetwork = currentNetwork;
	}

	public Device getCurrentServer() {
		return currentServer;
	}

	public void setCurrentServer(Device currentServer) {
		this.currentServer = currentServer;
	}

	public Terminal getTerminal() {
		return terminal;
	}

	public void setTerminal(Terminal terminal) {
		this.terminal = terminal;
	}

	public String getName() {
		return name;
	}
}