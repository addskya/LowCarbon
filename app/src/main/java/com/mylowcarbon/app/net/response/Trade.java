package com.mylowcarbon.app.net.response;

import java.util.List;

/**
 *
 */
public class Trade {

    public float todayAvgPrice;//余额

    public int last_id;
    public boolean list_more;
    public List<TradeDetail> coinList;
    public List<TradeTrend> tradeData;


    public static class TradeTrend {

        public int avg_price;
        public String time;


        public TradeTrend() {

        }
        public TradeTrend(int avg_price, String time ) {
            this.avg_price = avg_price;
            this.time = time;
        }


    }


}
