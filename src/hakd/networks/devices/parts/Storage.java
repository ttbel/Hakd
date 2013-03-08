package hakd.networks.devices.parts;

import hakd.networks.Network;
import hakd.networks.devices.Device;
import hakd.other.File;
import hakd.other.enumerations.FileType;
import hakd.other.enumerations.PartType;

import java.util.ArrayList;

public class Storage extends Part {
	private boolean			ssd;										// doubles the speed
	private int				capacity;									// in GB

	// storage ArrayLists
	private ArrayList<File>	osFiles			= new ArrayList<File>();	// operating system files, !FUN!
	private ArrayList<File>	userFiles		= new ArrayList<File>();	// random files people save
	private ArrayList<File>	programFiles	= new ArrayList<File>();	// (lua)programs able to run, is this copywritten, will Microsoft sue?
	private ArrayList<File>	logFiles		= new ArrayList<File>();	// these log arrays have infinite storage, thanks to a new leap in quantum
// physics

	public Storage(int level, Network network, Device device) {
		super(level, network, device);
		setType(PartType.STORAGE);

		switch (level) {
			case 0:
				setSpeed((level + 1) * 30 + (int) (Math.random() * 30));
				capacity = (int) Math.pow(2, (level + 4) + (int) (Math.random() * 3 - 1));
			default:
				setSpeed((level + 1) * 30 + ((int) (Math.random() * 60 - 30)));
				capacity = (int) Math.pow(2, (level + 4) + (int) (Math.random() * 3 + 1) - 2); // start at 16 GB at level 1 and make sure the OS takes
				// up 15 GB
				if (1 == (int) ((Math.random() * 30) + 1)) {
					ssd = true;
					setSpeed(getSpeed() * 2);
					capacity /= 2;
				}
		}

	}

	// adds a file to the end of one of the arraylists
	public void addFile(File file) {
		switch (file.getType()) {
			case OS:
				osFiles.add(file);
				break;
			case USER:
				osFiles.add(file);
				break;
			case PROGRAM:
				osFiles.add(file);
				break;
			default: // user
				break;
		}
	}

	// removes the first file with the specified name from the specified directory
	public void removeFile(FileType type, String name) {
		switch (type) {
			case OS:
				for (File f : osFiles) {
					if (f.getName() == name) {
						osFiles.remove(f);
					}
				}
				break;
			default:
				for (File f : userFiles) {
					if (f.getName() == name) {
						userFiles.remove(f);
					}
				}
				break;
			case PROGRAM:
				for (File f : programFiles) {
					if (f.getName() == name) {
						programFiles.remove(f);
					}
				}
				break;
			case LOG:
				for (File f : logFiles) {
					if (f.getName() == name) {
						logFiles.remove(f);
					}
				}
		}
	}

	// removes the first file with the specified name from the specified directory
	public File getFile(FileType type, String name) {
		switch (type) {
			case OS:
				for (File f : osFiles) {
					if (f.getName() == name) {
						return osFiles.get(osFiles.indexOf(f));
					}
				}
				break;
			default:
				for (File f : userFiles) {
					if (f.getName() == name) {
						return userFiles.get(userFiles.indexOf(f));
					}
				}
				break;
			case PROGRAM:
				for (File f : programFiles) {
					if (f.getName() == name) {
						return programFiles.get(programFiles.indexOf(f));
					}
				}
				break;
			case LOG:
				for (File f : logFiles) {
					if (f.getName() == name) {
						return logFiles.get(logFiles.indexOf(f));
					}
				}
		}
		return null;
	}

	public boolean isSsd() {
		return ssd;
	}

	public void setSsd(boolean ssd) {
		this.ssd = ssd;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public ArrayList<File> getOsFiles() {
		return osFiles;
	}

	public void setOsFiles(ArrayList<File> osFiles) {
		this.osFiles = osFiles;
	}

	public ArrayList<File> getUserFiles() {
		return userFiles;
	}

	public void setUserFiles(ArrayList<File> userFiles) {
		this.userFiles = userFiles;
	}

	public ArrayList<File> getProgramFiles() {
		return programFiles;
	}

	public void setProgramFiles(ArrayList<File> programFiles) {
		this.programFiles = programFiles;
	}

	public ArrayList<File> getLogFiles() {
		return logFiles;
	}

	public void setLogFiles(ArrayList<File> logFiles) {
		this.logFiles = logFiles;
	}
}
