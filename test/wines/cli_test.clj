(ns wines.cli-test
  (:require [clojure.test :refer :all]
            [wines.cli :refer :all]
            [clojure.string :as s]))

(def mocked-cli-args {:options {},
                      :arguments [],
                      :summary   "  -s, --search QUERY  Query string from cli\n  -h, --help          Prints help. GUI will start if no cli args passed.",
                      :errors nil})

(deftest parse-cli-args-test
  (testing "handles empty options"
    (with-redefs [println #(str %)]
      (is (nil? (parse-cli-args mocked-cli-args)))))

  (testing "handles help option"
    (with-redefs [println (fn [& args] (s/join args))]
      ; returns "", but prints help
      (is (= "" (parse-cli-args {:options {:help true}}))))))
