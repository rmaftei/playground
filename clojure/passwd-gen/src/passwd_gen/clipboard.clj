(ns passwd-gen.clipboard)

(import [java.awt.datatransfer StringSelection])


(defn ^sun.awt.windows.WClipboard get-clipboard []
  (-> (java.awt.Toolkit/getDefaultToolkit)
      (.getSystemClipboard)))

(get-clipboard) 