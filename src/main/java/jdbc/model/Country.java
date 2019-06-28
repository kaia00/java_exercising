package jdbc.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Country {

    private String code;
    private String name;
    private String continent;
    private String region;


}
