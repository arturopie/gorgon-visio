(defproject gorgon-visio "0.1.0-SNAPSHOT"
  :description "Tool for visualizing gorgon runs"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [com.github.kyleburton/teporingo "2.1.27"]]
  :main ^:skip-aot gorgon-visio.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
