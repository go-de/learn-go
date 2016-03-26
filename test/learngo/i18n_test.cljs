(ns learngo.i18n-test
  (:require [cljs.test      :refer-macros [is]]
            [devcards.core  :refer-macros [defcard-rg
                                           deftest
                                           start-devcard-ui!]]
            [learngo.i18n :as i18n]))

(deftest translate
  (is (= "falsch"
         (i18n/translate :wrong))))

(deftest unknown
  (is (= "unknown text"
         (i18n/translate :unknown-text))))
