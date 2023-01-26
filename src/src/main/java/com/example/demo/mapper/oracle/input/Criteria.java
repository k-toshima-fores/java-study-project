package com.example.demo.mapper.oracle.input;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.ToString;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

/**
 * 検索条件
 */
@EqualsAndHashCode
@ToString
public class Criteria {

  /** falseの場合に無視するBooleanフィールド */
  protected final Set<Field> falseIgnoreFields = new HashSet<>();

  /**
   * 指定されたフィールドを検索条件項目として取得する.
   * <p>
   * このメソッドはmapperファイル内から呼び出すことを想定している.
   *
   * @param fieldName フィールド名
   * @return 検索条件項目
   */
  public CriteriaItem get(String fieldName) {
    return new CriteriaItem(getValue(fieldName));
  }

  public List<String> likeEscapeAsList(String fieldName) {
    Object values = getValue(fieldName);
    if (!(values instanceof Collection)) {
      throw new IllegalArgumentException(String.format("Field \"%s\" is not an instance of Collection.", fieldName));
    }
    return ((Collection<?>) values).stream()
        .map(value -> new CriteriaItem(value).likeEscape().toString())
        .collect(Collectors.toList());
  }

  /**
   * 指定されたフィールドが検索条件として設定されているか判定する.
   * <p>
   * このメソッドはmapperファイル内から呼び出すことを想定している.
   *
   * @param fieldName フィールド名
   * @return true:設定されている / false:未設定
   */
  public boolean has(String fieldName) {
    Object value = getValue(fieldName);
    Field field = getField(fieldName);
    return hasValue(value, field);
  }

  @SneakyThrows
  protected Object getValue(String fieldName) {
    Field field = getField(fieldName);
    field.setAccessible(true);
    return field.get(this);
  }

  @SneakyThrows
  protected Field getField(String fieldName) {
    return this.getClass().getDeclaredField(fieldName);
  }

  protected boolean hasValue(Object value, Field field) {
    if (value instanceof Collection) {
      // 空のCollectionは未設定扱いとする
      if (CollectionUtils.isEmpty((Collection<?>) value)) {
        return false;
      }
      return ((Collection<?>) value).stream().anyMatch(item -> hasValue(item, field));
    } else if (value instanceof Map) {
      // 空のMapは未設定扱いとする
      if (CollectionUtils.isEmpty((Map<?, ?>) value)) {
        return false;
      }
      return ((Map<?, ?>) value).values().stream().anyMatch(item -> hasValue(item, field));
    } else if (value instanceof String) {
      // 空文字列は未設定扱いとする
      return StringUtils.isNotEmpty((String) value);
    } else if (value instanceof Boolean) {
      // falseを無視する対象のフィールド
      if (falseIgnoreFields.contains(field) && BooleanUtils.isFalse((Boolean) value)) {
        return false;
      }
    } else if (value instanceof Criteria) {
      // ネストした検索条件
      return !((Criteria) value).empty();
    }

    // 上記以外はnullを未設定扱いとする
    return value != null;
  }

  /**
   * 指定されたフィールドの値がfalseの場合、検索条件から無視する.
   * <p>
   * 画面上のチェックボックス項目で、常にtrueかfalseが入る(nullにならない)場合等に使用する.
   *
   * @param fieldNames フィールド名
   */
  public void ignoreIfFalse(String... fieldNames) {
    Stream.of(fieldNames)
        .map(this::getField)
        .forEach(falseIgnoreFields::add);
  }

  /**
   * 何も条件を指定していない場合
   */
  public boolean empty() {
    return Stream.of(getClass().getDeclaredFields()).filter(field -> has(field.getName())).count() <= 0;
  }

  public List<String> stringEscapeAsList(String fieldName) {
    Object values = getValue(fieldName);
    if (!(values instanceof Collection)) {
      throw new IllegalArgumentException(String.format("Field \"%s\" is not an instance of Collection.", fieldName));
    }
    return ((Collection<?>) values).stream()
        .map(value -> new CriteriaItem(value).toString())
        .collect(Collectors.toList());
  }
}
