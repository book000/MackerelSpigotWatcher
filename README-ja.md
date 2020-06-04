# MackerelSpigotWatcher

[Click here for English README](https://github.com/jaoafa/MackerelSpigotWatcher/blob/master/README.md)

[Mackerel](https://mackerel.io/) へ以下のサーバ情報を投げる Bukkit / Spigot プラグインです。

- オンラインプレイヤー数
- 1 分・5 分・15 分あたりの TPS 値
- `latest.log` のログレベルあたりのログ数 (SEVERE, WARNING, INFO, CONFIG, FINE, FINER, FINEST, ERROR)
- `latest.log` のログサイズ
- RAM free, total, max

## スクリーンショット

![Mackerel Screenshot](https://i.imgur.com/FtqINGG.png)

## ダウンロード・インストール

1. [Release](https://github.com/jaoafa/MackerelSpigotWatcher/releases) から最新のリリースをダウンロードし、`plugins` フォルダに配置します。
2. 一度サーバを起動し、`/stop` コマンドで停止してください。
3. [所属オーガニゼーション](https://mackerel.io/settings/user?tab=organizations) を開き、該当するオーガニゼーションの右側歯車を押し、「設定」を開きます。
4. オーガニゼーションの設定画面が開いたら、中央ナビゲーションバーから「API キー」を選択します。
5. 必要に応じ API キーを生成し、API キーをコピーしてください。
6. `plugins/MackerelSpigotWatcher/config.yml`を開き、`apikey: null` の `null` を先ほどコピーした API キーに置き換えます。 (例: `apikey: xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx`)
7. 保存し、サーバを起動してください。
8. Mackerel のダッシュボードまたはホストページに `custom.msw` のメトリックが表示されていれば成功です。

## メトリック名

- `custom.msw.player.*`: プレイヤーに関するメトリック
  - `custom.msw.player.count`: オンラインプレイヤー数
- `custom.msw.tps.*`: TPS に関するメトリック
  - `custom.msw.tps.1min`: 1 分当たりの TPS
  - `custom.msw.tps.5min`: 5 分当たりの TPS
  - `custom.msw.tps.15min`: 15 分当たりの TPS
- `custom.msw.log.*`: ログレベルあたりのログに関するメトリック
  - `custom.msw.log.severe`: ログレベル「`SEVERE`」のログ数
  - `custom.msw.log.warning`: ログレベル「`WARNING`」のログ数
  - `custom.msw.log.info`: ログレベル「`INFO`」のログ数
  - `custom.msw.log.config`: ログレベル「`CONFIG`」のログ数
  - `custom.msw.log.fine`: ログレベル「`FINE`」のログ数
  - `custom.msw.log.finest`: ログレベル「`FINEST`」のログ数
  - `custom.msw.log.error`: ログレベル「`ERROR`」のログ数
- `custom.msw.logsize`: ログファイル(`./logs/latest.log`)サイズ
- `custom.msw.memory.*`: Bukkit/Spigot(java Runtime)に割り当てられている RAM に関するメトリック (参考: [Runtime クラスの Javadoc](https://docs.oracle.com/javase/jp/8/docs/api/java/lang/Runtime.html))
  - `custom.msw.memory.free`: 使用されていないメモリサイズ
  - `custom.msw.memory.used`: 使用しているメモリサイズ
  - `custom.msw.memory.total`: 割り当てられているメモリサイズ
  - `custom.msw.memory.max`: 使用を試みるメモリサイズ

## ライセンス

このプロジェクトのライセンスはMITライセンスです。  
[LICENSE](https://github.com/jaoafa/MackerelSpigotWatcher/blob/master/LICENSE)