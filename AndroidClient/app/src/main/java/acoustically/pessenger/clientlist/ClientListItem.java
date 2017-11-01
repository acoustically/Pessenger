package acoustically.pessenger.clientlist;

/**
 * Created by acoustically on 17. 11. 1.
 */

public class ClientListItem {
  int id;
  String name;
  int isConnected;

  public ClientListItem(int id, String name, int isConnected) {
    this.id = id;
    this.name = name;
    this.isConnected = isConnected;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getConnected() {
    return isConnected;
  }
}
