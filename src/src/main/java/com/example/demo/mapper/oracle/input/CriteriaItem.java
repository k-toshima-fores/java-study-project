package com.example.demo.mapper.oracle.input;

import java.util.regex.Pattern;
import lombok.Value;

/**
 * 検索条件項目
 */
@Value
public class CriteriaItem {

  private static final Pattern ESCAPE_PATTERN = Pattern.compile("([_%\\\\])");

  Object value;

  /**
   * LIKE検索時の特殊文字をエスケープする
   *
   * @return エスケープ後の文字列
   */
  public CriteriaItem likeEscape() {
    if (this.value == null) {
      return this;
    }
    String escapedValue = ESCAPE_PATTERN.matcher(this.value.toString()).replaceAll("\\\\$1");
    return new CriteriaItem(escapedValue);
  }

  @Override
  public String toString() {
    if (this.value == null) {
      return "";
    }
    return this.value.toString();
  }
}
