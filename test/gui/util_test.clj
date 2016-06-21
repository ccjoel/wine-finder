(ns gui.util-test
  (:require
    [clojure.test :refer :all]
    [wines.util :refer [parse-json]]
    [gui.util :refer :all]))

(def mocked-body
      (:List
        (:Products
          (parse-json
            (slurp (clojure.java.io/resource "body.seed.json"))))))

(deftest find-wine-object-test
  (testing "correctly finds object from wine list"
    (is (= 145612 (:Id (first (find-wine-object "145612 Rombauer Chardonnay 2014" mocked-body))))))
  (testing "returns empty () if none found"
    (is (empty? (find-wine-object "1434612 Rombauer Chardonnay 2014" mocked-body)))))

(deftest format-key-test
  (testing "correctly formats vector-map key"
    (is
      (=
        ["Name" "Joel"]
        (format-key (first (seq {:Name "Joel"}))))))

  ;todo: handle bad input on both function and tests

  )

(def wine-list
  '({:Url "http://www.wine.com/v6/Rombauer-Chardonnay-2014/wine/145612/Detail.aspx", :PriceMax 44.99, :Retail nil, :Vintage "2014", :Labels [{:Id "145612m", :Name "thumbnail", :Url "http://cache.wine.com/labels/145612m.jpg"}], :Ratings {:HighestScore 92, :List []}, :Varietal {:Id 140, :Name "Chardonnay", :Url "http://www.wine.com/v6/Chardonnay/wine/list.aspx?N=7155+125+140", :WineType {:Id 125, :Name "White Wines", :Url "http://www.wine.com/v6/White-Wines/wine/list.aspx?N=7155+125"}}, :Id 145612, :Vineyard {:Id 8337, :Name "Rombauer Vineyards", :Url "http://www.wine.com/v6/Rombauer-Vineyards/learnabout.aspx?winery=90", :ImageUrl "http://cache.wine.com/aboutwine/basics/images/winerypics/90.jpg", :GeoLocation {:Latitude -360, :Longitude -360, :Url "http://www.wine.com/v6/aboutwine/mapof.aspx?winery=90"}}, :PriceRetail 36.0, :Vintages {:List []}, :Appellation {:Id 2454, :Name "Carneros", :Url "http://www.wine.com/v6/Carneros/wine/list.aspx?N=7155+101+2454", :Region {:Id 101, :Name "California", :Url "http://www.wine.com/v6/California/wine/list.aspx?N=7155+101", :Area nil}}, :Community {:Reviews {:HighestScore 0, :List [], :Url "http://www.wine.com/v6/Rombauer-Chardonnay-2014/wine/145612/Detail.aspx?pageType=reviews"}, :Url "http://www.wine.com/v6/Rombauer-Chardonnay-2014/wine/145612/Detail.aspx"}, :ProductAttributes [{:Id 616, :Name "Rich &amp; Creamy", :Url "http://www.wine.com/v6/Rich-andamp-Creamy/wine/list.aspx?N=7155+616", :ImageUrl ""} {:Id 0, :Name "Has Large Label", :Url "", :ImageUrl ""} {:Id 0, :Name "90+ Rated Wine", :Url "", :ImageUrl ""}], :GeoLocation {:Latitude -360, :Longitude -360, :Url "http://www.wine.com/v6/aboutwine/mapof.aspx?winery=90"}, :Name "Rombauer Chardonnay 2014", :Description "", :Type "Wine", :PriceMin 34.99})
  )

(def expected
  [["Url" "http://www.wine.com/v6/Rombauer-Chardonnay-2014/wine/145612/Detail.aspx"] ["PriceMax" 44.99] ["Retail" nil] ["Vintage" "2014"] ["Labels" [{:Id "145612m", :Name "thumbnail", :Url "http://cache.wine.com/labels/145612m.jpg"}]] ["Ratings" {:HighestScore 92, :List []}] ["Varietal" {:Id 140, :Name "Chardonnay", :Url "http://www.wine.com/v6/Chardonnay/wine/list.aspx?N=7155+125+140", :WineType {:Id 125, :Name "White Wines", :Url "http://www.wine.com/v6/White-Wines/wine/list.aspx?N=7155+125"}}] ["Id" 145612] ["Vineyard" {:Id 8337, :Name "Rombauer Vineyards", :Url "http://www.wine.com/v6/Rombauer-Vineyards/learnabout.aspx?winery=90", :ImageUrl "http://cache.wine.com/aboutwine/basics/images/winerypics/90.jpg", :GeoLocation {:Latitude -360, :Longitude -360, :Url "http://www.wine.com/v6/aboutwine/mapof.aspx?winery=90"}}] ["PriceRetail" 36.0] ["Vintages" {:List []}] ["Appellation" {:Id 2454, :Name "Carneros", :Url "http://www.wine.com/v6/Carneros/wine/list.aspx?N=7155+101+2454", :Region {:Id 101, :Name "California", :Url "http://www.wine.com/v6/California/wine/list.aspx?N=7155+101", :Area nil}}] ["Community" {:Reviews {:HighestScore 0, :List [], :Url "http://www.wine.com/v6/Rombauer-Chardonnay-2014/wine/145612/Detail.aspx?pageType=reviews"}, :Url "http://www.wine.com/v6/Rombauer-Chardonnay-2014/wine/145612/Detail.aspx"}] ["ProductAttributes" [{:Id 616, :Name "Rich &amp; Creamy", :Url "http://www.wine.com/v6/Rich-andamp-Creamy/wine/list.aspx?N=7155+616", :ImageUrl ""} {:Id 0, :Name "Has Large Label", :Url "", :ImageUrl ""} {:Id 0, :Name "90+ Rated Wine", :Url "", :ImageUrl ""}]] ["GeoLocation" {:Latitude -360, :Longitude -360, :Url "http://www.wine.com/v6/aboutwine/mapof.aspx?winery=90"}] ["Name" "Rombauer Chardonnay 2014"] ["Description" ""] ["Type" "Wine"] ["PriceMin" 34.99]]
  )

(deftest get-rows-test
  (testing "returns list of wine properties formatted as rows"
    (is
      (=
        expected
        (get-rows wine-list))))
  ; todo: what happens if bad input?
  )
