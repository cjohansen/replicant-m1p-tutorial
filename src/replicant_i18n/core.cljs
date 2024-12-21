(ns replicant-i18n.core
  (:require [m1p.core :as m1p]
            [replicant-i18n.i18n :as i18n]
            [replicant-i18n.i18n.en :as en]
            [replicant-i18n.i18n.nb :as nb]
            [replicant.dom :as r]))

(def dictionaries
  (-> {:nb nb/dictionary
       :en en/dictionary}
      (update-vals m1p/prepare-dictionary)))

(defn render-ui [user]
  [:div
   [:h1 [i18n/k :page/title]]
   [:p [i18n/k user :user/greeting]]
   [:button {:on {:click [:switch-locale]}}
    [i18n/k :locale/switch]]])

(defn render [state]
  (r/render
   (js/document.getElementById "app")
   (render-ui {:user/given-name "Christian"})
   {:alias-data
    {:dictionaries dictionaries
     :locale (:locale state)}}))

(def other-locale
  {:en :nb
   :nb :en})

(def store (atom {}))

(defn main []
  (r/set-dispatch!
   (fn [_ _]
     (swap! store update :locale other-locale)))

  (add-watch store ::render (fn [_ _ _ state] (render state)))
  (swap! store assoc :locale :en))
