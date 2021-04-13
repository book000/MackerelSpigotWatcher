# MackerelSpigotWatcher

[日本語の README はこちらから](https://github.com/jaoafa/MackerelSpigotWatcher/blob/master/README-ja.md)

PaperMC plugin that send the following server information to [Mackerel](https://mackerel.io/).

- Number of online players
- TPS value per 1 minute, 5 minutes, 15 minutes
- Number of logs per log level of `latest.log` (SEVERE, WARNING, INFO, CONFIG, FINE, FINER, FINEST, ERROR)
- `latest.log` log size
- RAM free, total, max

## Screenshot

![Mackerel Screenshot](https://i.imgur.com/FtqINGG.png)

## Download & Install

1. Download the latest release from [Release](https://github.com/jaoafa/MackerelSpigotWatcher/releases) and place it in the `plugins` folder.
2. Start the server once and stop it with the `/stop` command.
3. Open [your organization](https://mackerel.io/settings/user?tab=organizations) and press the right gear of the relevant organization to open "Settings".
4. When the organization settings page opens, select API Key from the central navigation bar.
5. Generate an API key if necessary and copy the API key.
6. Open `plugins/MackerelSpigotWatcher/config.yml` and replace the `null` in `apikey: null` with the API key you copied earlier. (Example: `apikey: xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx`)
7. Save and start the server.
8. You should be able to see the `custom.msw` metrics on your Mackerel dashboard or host page.

## Metric name

- `custom.msw.player.*`: Metrics for Player
  - `custom.msw.player.count`: Number of online players
  - `custom.msw.player.WORLD`: Number of online players per world (Added in 0.0.5)
- `custom.msw.tps.*`: Metrics for TPS
  - `custom.msw.tps.1min`: TPS per minute
  - `custom.msw.tps.5min`: TPS per 5 minutes
  - `custom.msw.tps.15min`: TPS per 15 minutes
- `custom.msw.log.*`: Metrics about logs per log level
  - `custom.msw.log.severe`: Number of logs with log level `SEVERE`
  - `custom.msw.log.warning`: Number of logs at log level `WARNING`
  - `custom.msw.log.info`: Number of logs at log level `INFO`
  - `custom.msw.log.config`: Number of logs of log level `CONFIG`
  - `custom.msw.log.fine`: Number of logs of log level `FINE`
  - `custom.msw.log.finest`: Number of logs at log level `FINEST`
  - `custom.msw.log.error`: Number of logs of log level `ERROR`
  - `custom.msw.log.debug`: Number of logs of log level `DEBUG` (Added in 0.0.4)
- `custom.msw.logsize`: Log file (`./logs/latest.log`) size
- `custom.msw.memory.*`: Metrics for RAM allocated to Bukkit/Spigot (java Runtime) (Reference: [Javadoc of Runtime class](https://docs.oracle.com/javase/8/docs/api/java/lang/Runtime.html))
  - `custom.msw.memory.free`: Free memory size
  - `custom.msw.memory.used`: Used memory size
  - `custom.msw.memory.total`: Allocated memory size
  - `custom.msw.memory.max`: Memory size to try use
- `custom.msw.world_*.*`: World information running on Bukkit/Spigot (Added in 0.0.3)
  - `custom.msw.world_loadedchunk.WORLD`: Number of chunks loaded in the world
  - `custom.msw.world_entitys.WORLD`: Number of entities in the world
  - `custom.msw.world_tileentitys.WORLD`: Number of tile entities in the world

## License

The license for this project is MIT License.  
[LICENSE](https://github.com/jaoafa/MackerelSpigotWatcher/blob/master/LICENSE)
