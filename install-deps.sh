while read DEP; do
  rm -rf /tmp/$DEP
  git clone https://github.com/Androbin/$DEP /tmp/$DEP
  cp -r /tmp/$DEP/* .
done < full-deps.txt
