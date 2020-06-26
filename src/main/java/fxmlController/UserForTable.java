package fxmlController;

import javafx.beans.property.SimpleStringProperty;

public class UserForTable {
    private final SimpleStringProperty name;
    private final SimpleStringProperty type;

    public UserForTable ( String name, String type ) {
        this.name = new SimpleStringProperty ( name );
        this.type = new SimpleStringProperty ( type );
    }

    public String getName () {
        return name.get ();
    }

    public String getType () {
        return type.get ();
    }

}
