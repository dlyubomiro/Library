package bg.sofia.fmi.mjt.library.utills.library;

import java.util.List;

public record VolumeInfo(String title,
                         List<String> authors,
                         String publisher,
                         String publishedDate,
                         String description,
                         List<IndustryID> industryIdentifiers,
                         ReadingModes readingModes,
                         int pageCount,
                         String printType,
                         List<String> categories,
                         double averageRating,
                         int ratingsCount,
                         String maturityRating,
                         boolean allowAnonLogging,
                         String contentVersion,
                         PanelizationSummary panelizationSummary,
                         ImageLinks imageLinks,
                         String language,
                         String previewLink,
                         String infoLink,
                         String canonicalVolumeLink) {
    public record IndustryID(String type, String identifier) {
    }

    public record ReadingModes(boolean text, boolean image) {
    }

    public record PanelizationSummary(boolean containsEpubBubbles, boolean containsImageBubbles) {
    }

    public record ImageLinks(String smallThumbnail, String thumbnail) {
    }
}
