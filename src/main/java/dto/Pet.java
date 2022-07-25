package dto;

import java.util.List;
import lombok.Data;

@Data
public class Pet {
    private List<String> photoUrls;
    private String name;
    private int id;
    private CategoryPet category;
    private List<TagsItemPet> tags;
    private String status;
}