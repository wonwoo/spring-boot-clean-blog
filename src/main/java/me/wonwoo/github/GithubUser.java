package me.wonwoo.github;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by wonwoo on 2016. 8. 23..
 */
public class GithubUser implements Serializable {

  private String email;

  private String name;

  private String company;

  private String blog;

  private String avatar;

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCompany() {
    return this.company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getBlog() {
    return this.blog;
  }

  public void setBlog(String blog) {
    this.blog = blog;
  }

  public String getAvatar() {
    return avatar;
  }

  @JsonProperty("avatar_url")
  public void setAvatarUrl(String avatar) {
    this.avatar = avatar;
  }

}