/**
 *
 *  @author Tkaczyk Bartłomiej S22517
 *
 */

package zad1;


import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class Tools {
    public static Options createOptionsFromYaml(String fileName) {
        try (InputStream input = new FileInputStream(fileName)){
            Yaml yaml = new Yaml();
            Map<String, Object> objectMap = yaml.load(input);
            Options options = new Options(
                    (String) objectMap.get("host"),
                    (Integer) objectMap.get("port"),
                    (Boolean) objectMap.get("concurMode"),
                    (Boolean) objectMap.get("showSendRes"),
                    (Map<String, List<String>>) objectMap.get("clientsMap")
            );
            return options;
        } catch (IOException e ){
            return null;
        }
    }
}
