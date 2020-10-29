(ns status-im.ui.screens.chat.image.preview.views
  (:require [status-im.ui.components.colors :as colors]
            [status-im.ui.components.react :as react]
            [reagent.core :as reagent]
            [quo.core :as quo]
            [status-im.i18n :as i18n]
            [status-im.ui.components.icons.vector-icons :as icons]
            [status-im.ui.screens.chat.sheets :as sheets]
            [quo.components.safe-area :as safe-area]
            ["react-native-image-viewing" :default image-viewing]))

(defn footer []
  (let [show-sheet (reagent/atom false)]
    (fn [{:keys [on-close message]}]
     [safe-area/consumer
      (fn [insets]
        [react/view {:style {:position       "absolute"
                             :left           0
                             :right          0
                             :bottom         0
                             :padding-bottom (:bottom insets)}}
         [react/view {:style {:flex-direction     :row
                              :padding-horizontal 24
                              :justify-content    :center
                              :align-items        :center}}
          [react/touchable-opacity {:on-press on-close
                                    :style  {:background-color   colors/black-transparent-86
                                             :padding-horizontal 24
                                             :padding-vertical   11
                                             :border-radius      44}}
           [quo/text {:style {:color colors/white-persist}}
            (i18n/label :t/close)]]

          ;; NOTE(Ferossgp): If we use global bottom sheet, then it is rendered under the preview
          [quo/bottom-sheet {:visible?  @show-sheet
                             :on-cancel #(reset! show-sheet false)}
           [sheets/image-long-press message #(do (reset! show-sheet false)
                                                 (on-close))]]
          [react/touchable-opacity
           {:on-press #(reset! show-sheet true)
            :style    {:background-color colors/black-transparent-86
                       :border-radius    44
                       :padding          8
                       :position         :absolute
                       :bottom           0
                       :right            24}}
           [icons/icon :main-icons/more {:container-style {:width  24
                                                           :height 24}
                                         :color           colors/white-persist}]]]])])))

(defn preview-image [{{:keys [content] :as message} :message
                      visible                       :visible
                      on-close                      :on-close}]
  [:> image-viewing {:images           #js [#js {:uri (:image content)}]
                     :on-request-close on-close
                     :hideHeaderOnZoom false
                     :hideFooterOnZoom false
                     :HeaderComponent  #(reagent/as-element [:<>]) ; NOTE: Keep it to remove default header
                     :FooterComponent  #(reagent/as-element [footer {:on-close on-close
                                                                     :message  message}])
                     :visible          visible}])
