package model;

import java.util.List;

public interface Item extends Renderable {
    String getName();

    List<String> getDescription();
}
