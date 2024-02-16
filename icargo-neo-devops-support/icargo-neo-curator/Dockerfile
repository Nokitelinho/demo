FROM untergeek/curator:5.8.4


ADD curator/ /curator/

ENTRYPOINT ["/curator/curator", "--config", "/curator/curator-cfg.yml", "/curator/curator-actions.yml"]
