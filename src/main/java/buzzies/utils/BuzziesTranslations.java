package buzzies.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;


// kinda just yonked from Carpet Extra sorries
public class BuzziesTranslations
{
    private static final ClassLoader _resources = BuzziesTranslations.class.getClassLoader();
    private static final Gson _gson = new Gson();

    public static Map<String, String> getTranslation(String lang)
    {
        InputStream languageFile = _resources.getResourceAsStream("assets/buzzies/lang/%s.json".formatted(lang));
        if (languageFile == null)
        {
            return Collections.emptyMap();
        }

        String fileContent;
        try
        {
            fileContent = IOUtils.toString(languageFile, StandardCharsets.UTF_8);
        }
        catch (IOException e)
        {
            return Collections.emptyMap();
        }

        return _gson.fromJson(fileContent, new TypeToken<Map<String, String>>() {}.getType());
    }
}
