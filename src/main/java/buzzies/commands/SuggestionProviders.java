package buzzies.commands;

import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SuggestionProviders {
    public static CompletableFuture<Suggestions> stringSuggestions(SuggestionsBuilder builder, String... suggestions) {
        for (String suggestion : suggestions)
            builder.suggest(suggestion);
        return builder.buildFuture();
    }

    public static CompletableFuture<Suggestions> stringSuggestions(SuggestionsBuilder builder, List<String> suggestions) {
        for (String suggestion : suggestions)
            builder.suggest(suggestion);
        return builder.buildFuture();
    }
}
