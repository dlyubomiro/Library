package bg.sofia.fmi.mjt.library.utills.library;

public record BookRaw(String kind, String id, String eTag,
                      String selfLink, VolumeInfo volumeInfo,
                      Object saleInfo, Object accessInfo, Object searchInfo) {
}
