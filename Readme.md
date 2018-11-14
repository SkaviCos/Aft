### Run

Optimus runs your tests in parallel by default. Just connect the devices you need coverage on and choose between one of the following modes to trigger your test run
1. `Fragmentation`      
2. `Distribution`    

#### Step 1: Connect devices(Android or IOs) or create an Android Emulator.
#### Step 2: Open your favourite terminal and navigate to the project folder.
#### Step 3: Run
    gradle runFragmentation -DtestFeed=<Android/iOS> -Dtags=@<scope_tag>
