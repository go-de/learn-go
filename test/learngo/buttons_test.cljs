(ns learngo.buttons-test
  (:require [cljs.test       :as t]
            [devcards.core   :refer-macros [defcard-rg]]
            [learngo.buttons :as buttons]
            [reagent.core    :as r]))

(defcard-rg button-group
  [buttons/group [[:foo #(.log js/console "foo")]
                  [:bar #(.log js/console "bar")]
                  [:baz #(.log js/console "baz")]]
   (r/atom {:foo :active :baz :disabled})])
