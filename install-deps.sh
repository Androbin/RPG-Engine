#!/bin/bash
set -e

open=()
closed=()

while read -r dep; do
  open+=("$dep")
done < deps.txt

while [ -n "${open[*]}" ]; do
  for dep in "${open[@]}"; do
    git clone https://github.com/Androbin/"$dep" /tmp/"$dep"
    cp -r /tmp/"$dep"/* .
  done
  
  closed+=("${open[@]}")
  new=()
  
  for dep1 in "${open[@]}"; do
    while read -r dep2; do
      contains=false
      
      for dep3 in "${closed[@]}" "${new[@]}"; do
        if [[ "$dep3" == "$dep2" ]]; then
          contains=true
        fi
      done
      
      if ! [[ "$contains" == true ]]; then
        new+=("$dep2")
      fi
    done < /tmp/"$dep1"/deps.txt
  done
  
  open=("${new[@]}")
done
