(ns replicant-i18n.i18n
  (:require [m1p.core :as m1p]
            [replicant.alias :refer [defalias]]))

(defalias k [params [k]]
  (let [{:keys [dictionaries locale]} (:replicant/alias-data params)]
    (m1p/lookup {} (get dictionaries locale) k params)))
