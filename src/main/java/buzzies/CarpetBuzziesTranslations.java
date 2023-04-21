package buzzies;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;


// kinda just yonked from Carpet Extra sorries
public class CarpetBuzziesTranslations {
    private static final List<String> supportedLanguages = List.of("en_us");
    private static final ClassLoader resources = CarpetBuzziesTranslations.class.getClassLoader();
    private static final Gson gson = new Gson();

    public static Map<String, String> getTranslation(String lang) {
        if (!supportedLanguages.contains(lang)) {
            return Collections.emptyMap();
        }

        InputStream languageFile = resources.getResourceAsStream("assets/buzzies/lang/%s.json".formatted(lang));
        String fileContent;
        try {
            fileContent = IOUtils.toString(languageFile, StandardCharsets.UTF_8);
        } catch (IOException e) {
            return Collections.emptyMap();
        }

        return gson.fromJson(fileContent, new TypeToken<Map<String, String>>(){}.getType());
    }
}
