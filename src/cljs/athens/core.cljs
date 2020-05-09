(ns athens.core
  (:require
   [athens.config :as config]
   #_[athens.db :as db]
   [athens.events]
   #_[athens.parser :refer [parser]]
   [athens.router :as router]
   [athens.subs]
   [athens.views :as views]
   [re-frame.core :as rf]
   [reagent.core :as reagent]
   #_[re-posh.core :as rp]
   ))

(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (rf/clear-subscription-cache!)
  (router/init-routes!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn init []
  (rf/dispatch-sync [:init-rfdb])
  ;; when dev, download datoms directly
  ;; FIXME without this dispatch nothing works, so enabling it for now
  (when true #_config/debug?
    (rf/dispatch [:boot]))
  (dev-setup)
  (mount-root))
