# How To:
0. (First Time only) pair the robot with the pc through bluetooth menu.

1. turn on robot.

2. check name is correct and click connect.

3. wait till connected.

4. press button on robot to start program.

5. Robot will stream data and you can send commands to robot.

6. When done, click disconnect and then turn off robot.
	(the program won't automatically notify if it has been disconnected. Just toggle the disconnect button manually)

# String Formats:

The Terminal accepts certain string formats to display data on the right see below.

## Send Cell data:
(Updates cell at pos x y from bottom left (0,0))
	C_x_y_LightLevel_N_S_E_W
### Example:
	"C_1_0_95_316_-1_399_278\n"
	
## IR Data:
	SR_FL_F_FR_R_BR_B_BL_L
### Example:
	"SR_72_316_82_399_82_28_59_278\n"
## LDR Data:
	SL_LDR
### Example:
	"SL_95\n"
 
## Encoder Data
	SE_LEncoder_REncoder
	
### Example:
	"SE_35_20\n"

#Hints: 
	Build up the strings using sprintf.
	If there are problems close and restart it.
	There will be bugs in this.

