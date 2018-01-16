if [ ! -d "src/main/resources/openalpr" ]; then
    sudo apt-get install libleptonica-dev -y
    sudo apt-get install cmake -y
    sudo apt-get install libtesseract-dev -y
    sudo apt-get install libopencv-dev -y
    sudo apt-get install libcurl4-openssl-dev -y
    sudo apt-get install liblog4cplus-dev -y
    git clone https://github.com/openalpr/openalpr.git src/main/resources/openalpr
    cd src/main/resources/openalpr/src
    mkdir build
    cd build
    cmake -DCMAKE_INSTALL_PREFIX:PATH=/usr -DCMAKE_INSTALL_SYSCONFDIR:PATH=/etc ..
    make
    sudo make install
fi
