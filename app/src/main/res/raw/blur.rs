#pragma version(1)
#pragma rs java_package_name(com.example.app)

rs_allocation in;
rs_allocation out;
rs_script blurScript;

static const float4 weight = {0.2270270270, 0.3162162162, 0.0702702703, 0.3162162162, 0.4540540541, 0.0702702703, 0.2270270270, 0.3162162162, 0.0702702703};

void root(const uchar4 *v_in, uchar4 *v_out, const void *usrData, uint32_t x, uint32_t y) {
    float4 sum = 0;

    for (int i = -1; i <= 1; i++) {
        for (int j = -1; j <= 1; j++) {
            sum += rsUnpackColor8888(rsGetElementAt_uchar4(in, x + i, y + j)) * weight[(i + 1) * 3 + (j + 1)];
        }
    }

    rsSetElementAt_uchar4(out, rsPackColorTo8888(sum), x, y);
}