package me.wonwoo.config;

/**
 * Created by wonwoo on 2016. 9. 6..
 */
public enum Section {
  HOME("Home"),
  POST("Post"),
  CATEGORY("Category");

  private String value;

  Section(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
