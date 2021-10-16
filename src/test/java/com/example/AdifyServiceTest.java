package com.example;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdifyServiceTest {

  class SenderSpy implements Subscription.Sender {
    String event;
    String body;

    @Override
    public void send(String event, String body) {
      this.event = event;
      this.body = body;
    }
  }

  @Test
  @Tag("slow")
  void foo() {
    SenderSpy spy = new SenderSpy();
    AdifyService a = new AdifyService(new Adify(new HerokuGetRequest("adify")), "SESSION_ID,USER_ID,PRODUCT_ID", spy);
    a.execute();
    assertEquals("display", spy.event);
  }

  @Test
  @Tag("slow")
  void fetchProductPageEvent_sendsDisplayEvent() {
    SenderSpy spy = new SenderSpy();
    AdifyService a = new AdifyService(new Adify(new HerokuGetRequest("adify")), "SESSION_ID,USER_ID,PRODUCT_ID", spy);
    a.execute();
    assertEquals("SESSION_ID,advert,PRODUCT_ID,PRODUCT_NAME", spy.body);
  }

  @Test
  @Tag("fast")
  void adifyServiceExecute_gatefetchNotNull() {
    SenderSpy spy = new SenderSpy();
    AdifyService a = new AdifyService(new Adify(new HerokuGetRequest("adify")), "SESSION_ID,USER_ID,PRODUCT_ID", spy);
    a.execute();
    assertEquals("SESSION_ID", spy.body.split(",")[0]);
    assertEquals("USER_ID", spy.body.split(",")[1]);
    assertEquals("PRODUCT_ID", spy.body.split(",")[2]);
  }

}