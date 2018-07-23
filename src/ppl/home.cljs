(ns ppl.home
  (:require [accountant.core :as accountant]
            [reagent.core :as r]
            [antizer.reagent :as ant]))

(defn side-menu []
  [ant/menu {:mode "inline" :theme "dark" :style {:height "100%"}}
   [ant/menu-item "Menu Item"]
   [ant/menu-sub-menu {:title "Sub Menu"}
    [ant/menu-item
     [:div {:on-click #(accountant/navigate! "/about")} "About PPL"]]
    [ant/menu-item "Item 2"]]])

(defn content-area []
  [ant/layout-content {:class "content-area"}
   [ant/row {:gutter 12}]
   [ant/button {:on-click #(ant/message-info "Hello Reagent!")} "Click Me!"]])

(defn render-layout []
  (fn []
    [ant/layout
     [ant/affix
      [ant/layout-header {:class "banner"}
       (r/as-element
        [ant/row
         [ant/col {:span 12} [:h2.banner-header "Proud Parents of Loss"]]
         [ant/col {:span 1 :offset 11}
          [:a {:href "http://www.proudparentsofloss.org"}
           [ant/icon {:class "banner-logo" :type "github"}]]]])]]
     [ant/layout
      [ant/layout-sider [side-menu]]
      [ant/layout {:style {:width "60%"}}
       [content-area]]]]))

(defn home-page []
  [render-layout])
