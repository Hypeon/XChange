package org.knowm.xchange.dto.marketdata;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** Data object representing a Trade */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "trade_category", discriminatorType = DiscriminatorType.STRING)
public class Trade implements Serializable {

  /** The trade id */
  @Id protected Long id;

  /** Did this trade result from the execution of a bid or a ask? */
  @Enumerated(EnumType.STRING)
  protected OrderType type;

  /** Amount that was traded */
  @Column(name = "original_amount", scale = 2)
  protected BigDecimal originalAmount;

  /** The currency pair */
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "currency_pair_id")
  protected CurrencyPair currencyPair;

  /** The price */
  @Column(precision = 9, scale = 8)
  protected BigDecimal price;

  /** The timestamp of the trade according to the exchange's server, null if not provided */
  protected Date timestamp;

  public Trade() {}

  /**
   * This constructor is called to create a public Trade object in {@link
   * MarketDataService#getTrades(org.knowm.xchange.currency.CurrencyPair, Object...)}
   * implementations) since it's missing the orderId and fee parameters.
   *
   * @param type The trade type (BID side or ASK side)
   * @param originalAmount The depth of this trade
   * @param price The price (either the bid or the ask)
   * @param timestamp The timestamp of the trade according to the exchange's server, null if not
   *     provided
   * @param id The id of the trade
   */
  public Trade(
      OrderType type,
      BigDecimal originalAmount,
      CurrencyPair currencyPair,
      BigDecimal price,
      Date timestamp,
      Long id) {

    this.type = type;
    this.originalAmount = originalAmount;
    this.currencyPair = currencyPair;
    this.price = price;
    this.timestamp = timestamp;
    this.id = id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setType(OrderType type) {
    this.type = type;
  }

  public void setOriginalAmount(BigDecimal originalAmount) {
    this.originalAmount = originalAmount;
  }

  public void setCurrencyPair(CurrencyPair currencyPair) {
    this.currencyPair = currencyPair;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public OrderType getType() {

    return type;
  }

  public BigDecimal getOriginalAmount() {

    return originalAmount;
  }

  public CurrencyPair getCurrencyPair() {

    return currencyPair;
  }

  public BigDecimal getPrice() {

    return price;
  }

  public Date getTimestamp() {

    return timestamp;
  }

  public Long getId() {

    return id;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    return this.id.equals(((Trade) o).getId());
  }

  @Override
  public int hashCode() {

    return Objects.hashCode(id);
  }

  @Override
  public String toString() {

    return "Trade [type="
        + type
        + ", originalAmount="
        + originalAmount
        + ", currencyPair="
        + currencyPair
        + ", price="
        + price
        + ", timestamp="
        + timestamp
        + ", id="
        + id
        + "]";
  }

  public static class Builder {

    protected OrderType type;
    protected BigDecimal originalAmount;
    protected CurrencyPair currencyPair;
    protected BigDecimal price;
    protected Date timestamp;
    protected Long id;

    public static Builder from(Trade trade) {
      return new Builder()
          .type(trade.getType())
          .originalAmount(trade.getOriginalAmount())
          .currencyPair(trade.getCurrencyPair())
          .price(trade.getPrice())
          .timestamp(trade.getTimestamp())
          .id(trade.getId());
    }

    public Builder type(OrderType type) {

      this.type = type;
      return this;
    }

    public Builder originalAmount(BigDecimal originalAmount) {

      this.originalAmount = originalAmount;
      return this;
    }

    public Builder currencyPair(CurrencyPair currencyPair) {

      this.currencyPair = currencyPair;
      return this;
    }

    public Builder price(BigDecimal price) {

      this.price = price;
      return this;
    }

    public Builder timestamp(Date timestamp) {

      this.timestamp = timestamp;
      return this;
    }

    public Builder id(Long id) {

      this.id = id;
      return this;
    }

    public Trade build() {

      return new Trade(type, originalAmount, currencyPair, price, timestamp, id);
    }
  }
}
