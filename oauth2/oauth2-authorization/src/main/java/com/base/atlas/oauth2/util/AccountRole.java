package com.base.atlas.oauth2.util;

/**
 * spring security 账户角色
 *
 * @author renfei
 */
public enum AccountRole {
  ROLE_USER("ROLE_USER"), ROLE_ADMIN("ROLE_ADMIN"), ROLE_SYSADMIN("ROLE_SYSADMIN");

  AccountRole(String rule) {
    this.role = role;
  }

  private String role;

  public String getRole() {
    return role;
  }
}
