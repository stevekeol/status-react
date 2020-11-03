(ns status-im.clipboard.core
  (:require ["react-native" :refer (NativeModules)]
            [quo.platform :as platform]))

(def clipboard-manager (when platform/ios? (.-MediaClipboard ^js NativeModules)))

(defn copy-image [base64 on-success on-error]
  (when platform/ios?
    (-> (.copyImage ^js clipboard-manager base64)
        (.then on-success)
        (.catch on-error))))

(defn paste [on-success on-error]
  (when platform/ios?
    (-> (.paste ^js clipboard-manager)
        (.then on-success)
        (.catch on-error))))

(defn has-image [on-success on-error]
  (when platform/ios?
    (->
     (.hasImages ^js clipboard-manager)
     (.then on-success)
     (.catch on-error))))
