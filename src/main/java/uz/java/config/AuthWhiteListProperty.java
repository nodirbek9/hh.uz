package uz.java.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "auth")
@Getter
@Setter
public class AuthWhiteListProperty {

    private List<String> whiteList = new ArrayList<>();
}
