(ns ppl.core
  (:require [reagent.core :as r]
            [ppl.about :refer [about-page]]
            [ppl.home :refer [home-page]]
            [goog.events :as events]
            [goog.history.EventType :as EventType]
            [accountant.core :as accountant]
            [secretary.core :as secretary :include-macros true])
  (:import goog.History))

(def selected-page (r/atom home-page))

(defn page []
  [@selected-page])

;; -------------------------
;; routes

(secretary/defroute "/" []
  (reset! selected-page home-page))

(secretary/defroute "/about" []
  (reset! selected-page about-page))

;; -------------------------
;; history

(defn hook-browser-navigation! []
  (doto (History.)
    (events/listen
     EventType/NAVIGATE
     (fn [event]
       (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

;; -------------------------
;; Initialize app

(defn stop []
  (js/console.log "Stopping..."))

(defn start []
  (js/console.log "Starting...")
  (r/render [page]
            (.getElementById js/document "app")))

(defn ^:export init []
  (accountant/configure-navigation!
   {:nav-handler
    (fn [path]
      (secretary/dispatch! path))
    :path-exists?
    (fn [path]
      (secretary/locate-route path))})
  (accountant/dispatch-current!)
  (start))
